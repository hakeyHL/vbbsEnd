package com.bbs.service.impl;

import com.bbs.mybatis.inter.UserVoteMapper;
import com.bbs.mybatis.inter.VoteCandidateMapper;
import com.bbs.mybatis.inter.VoteMapper;
import com.bbs.mybatis.model.*;
import com.bbs.service.IVoteService;
import com.bbs.util.DateUtil;
import com.bbs.util.TextUtils;
import com.bbs.util.excel.ExcelImporter;
import com.bbs.util.excel.ImportCallBack;
import com.bbs.util.excel.ImportConfig;
import com.bbs.util.page.Page;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Workbook;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by lihongde on 2016/9/12 15:53
 */
@Service
public class VoteServiceImpl implements IVoteService {

    private Logger logger = LoggerFactory.getLogger(ExcelImporter.class);

    @Resource
    private VoteMapper voteMapper;

    @Resource
    ExcelImporter importer;

    @Resource
    private VoteCandidateMapper voteCandidateMapper;

    @Resource
    private UserVoteMapper userVoteMapper;

    private static final String IMPORT_SQL = "insert into vote_candidate(vote_id, name, avatar, introduction) values(?, ?, ?, ?)";


    @Override
    public Page<Vote> findPageVotes(String post, int page, int size) {
        VoteExample example = new VoteExample();
        VoteExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(post)) {
            criteria.andPostIdEqualTo(Integer.parseInt(post));
        }
        RowBounds rowBounds = new RowBounds((page - 1) * size, size);
        List<Vote> list = voteMapper.selectByExampleWithRowbounds(example, rowBounds);
        return new Page<Vote>(page, size, voteMapper.countByExample(example), list);
    }


    @Override
    public void addOrUpdateVote(Vote vote) {
        Integer id = vote.getId();
        if(id == null){
            voteMapper.insertSelective(vote);
        }else{
            voteMapper.updateByPrimaryKey(vote);
        }
    }

    @Override
    public Map<String, Object> importExcelFile(MultipartFile candidateFile, Integer voteId) throws Exception{
        return importer.setImportConfig(new ImportConfig() {
        @Override
        public String validation(Workbook xwb) {
            return null;
        }

        @Override
        public String getImportSQL() {
            return IMPORT_SQL;
        }

        @Override
        public List<Object[]> getImportData(Workbook wxb, List<Object[]> data) throws Exception{

            List<PictureData> picList = (List<PictureData>) wxb.getAllPictures();
            List<Object[]> dataQueue = new ArrayList<Object[]>();
            for(int i=0; i<data.size(); i++){
                Object[] tempData = new Object[4];
                tempData = data.get(i);
                if(picList != null && picList.size() > 0){
                    tempData[2] =  readExcelImages(picList, i);
                }else{
                    tempData[2] = "";
                }
                dataQueue.add(tempData);
            }
            return dataQueue;
        }

        @Override
        public ImportCallBack getImportCallBack() {
            return new ImportCallBack() {
                @Override
                public void preOperation(SqlSessionTemplate sqlSessionTemplate, List<Object[]> data) {

                }

                @Override
                public void postOperation (SqlSessionTemplate sqlSessionTemplate, List < Object[]>data){

                }
            };

        }
    }).importExcelFile(candidateFile, voteId);
    }

    @Override
    public Vote getVote(Integer voteId) {
        return voteMapper.selectByPrimaryKey(voteId);
    }

    @Override
    public List<VoteCandidate> findCandidateByVoteId(Integer voteId) {
        VoteCandidateExample example = new VoteCandidateExample();
        VoteCandidateExample.Criteria criteria = example.createCriteria();
        criteria.andVoteIdEqualTo(voteId);
        return voteCandidateMapper.selectByExample(example);
    }

    @Override
    public VoteCandidate getCandidate(Integer voteId, String name) {
        VoteCandidateExample example = new VoteCandidateExample();
        VoteCandidateExample.Criteria criteria = example.createCriteria();
        criteria.andVoteIdEqualTo(voteId);
        criteria.andNameEqualTo(name);
        return voteCandidateMapper.selectByExample(example).get(0);
    }

    @Override
    public void updateCandidate(VoteCandidate candidate) {
        voteCandidateMapper.updateByPrimaryKey(candidate);
    }

    @Override
    public void addUserVote(UserVote userVote) {
        userVoteMapper.insert(userVote);
    }

    @Override
    public List<UserVote> findUserVote(Integer userId, Integer voteId) {
        UserVoteExample example = new UserVoteExample();
        UserVoteExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andVoteIdEqualTo(voteId);
        criteria.andVoteTimeBetween(DateUtil.getStartTimeOfDate(new Date()), DateUtil.getEndTimeOfDate(new Date()));
        return userVoteMapper.selectByExample(example);
    }

    private String readExcelImages(List<PictureData> picList, int index) throws IOException {
        String coverImageName = "";
        String realPath = TextUtils.getConfig("vote_candidate_avatar", this);
        for (int i=0; i<picList.size(); i++) {
            if(index == i){
                PictureData pictureData = picList.get(i);
                coverImageName = UUID.randomUUID().toString() +  ".png" ;
                FileUtils.writeByteArrayToFile(new File(realPath, coverImageName), pictureData.getData());
                logger.info("writing image of:{} to realPath:{}", coverImageName, realPath);
            }

        }

        return realPath + coverImageName;
    }

}
