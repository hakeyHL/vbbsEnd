package com.bbs.util;

import org.apache.commons.lang.StringUtils;
import org.dom4j.*;

import java.util.*;

/**
 * Created by lihongde on 2016/9/14 14:38
 */
public class XmlUtil {

    public static Map<String, Object> xml2map(String xml) throws DocumentException {
        org.dom4j.Document doc = DocumentHelper.parseText(xml);
        Element body = doc.getRootElement();
        Map<String, Object> vo = buildXmlBody2map(body);
        return vo;
    }

    private static Map<String, Object> buildXmlBody2map(Element body) {
        Map vo = new HashMap();
        if (body != null) {
            List<Element> elements = body.elements();
            for (Element element : elements) {
                String key = element.getName();
                if (StringUtils.isNotEmpty(key)) {
                    String type = element.attributeValue("type", "java.lang.String");
                    String text = element.getText().trim();
                    Object value = null;
                    if (java.lang.String.class.getCanonicalName().equals(type)) {
                        value = text;
                    } else if (java.lang.Character.class.getCanonicalName().equals(type)) {
                        value = new java.lang.Character(text.charAt(0));
                    } else if (java.lang.Boolean.class.getCanonicalName().equals(type)) {
                        value = new java.lang.Boolean(text);
                    } else if (java.lang.Short.class.getCanonicalName().equals(type)) {
                        value = java.lang.Short.parseShort(text);
                    } else if (java.lang.Integer.class.getCanonicalName().equals(type)) {
                        value = java.lang.Integer.parseInt(text);
                    } else if (java.lang.Long.class.getCanonicalName().equals(type)) {
                        value = java.lang.Long.parseLong(text);
                    } else if (java.lang.Float.class.getCanonicalName().equals(type)) {
                        value = java.lang.Float.parseFloat(text);
                    } else if (java.lang.Double.class.getCanonicalName().equals(type)) {
                        value = java.lang.Double.parseDouble(text);
                    } else if (java.math.BigInteger.class.getCanonicalName().equals(type)) {
                        value = new java.math.BigInteger(text);
                    } else if (java.math.BigDecimal.class.getCanonicalName().equals(type)) {
                        value = new java.math.BigDecimal(text);
                    } else if (java.util.Map.class.getCanonicalName().equals(type)) {
                        value = buildXmlBody2map(element);
                    } else {
                    }
                    vo.put(key, value);
                }
            }
        }
        return vo;
    }
}
