package com.minis.batis;

import com.minis.beans.factory.annotation.AutoWired;
import com.minis.jdbc.core.JdbcTemplate;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DefaultSqlSessionFactory implements SqlSessionFactory{
    @AutoWired
    JdbcTemplate jdbcTemplate;
    String mapperLocations;
    Map<String,MapperNode> mapperNodeMap = new HashMap<>();

    public DefaultSqlSessionFactory() {
    }

    public void init() {
        scanLocation(this.mapperLocations);
        for (Map.Entry<String, MapperNode> entry : this.mapperNodeMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println("********DefaultSqlSessionFactory init*********");
    }

    @Override
    public SqlSession openSession() {
        SqlSession newSqlSession = new DefaultSqlSession();
        newSqlSession.setJdbcTemplate(jdbcTemplate);
        newSqlSession.setSqlSessionFactory(this);

        return newSqlSession;
    }


    private void scanLocation(String location) {
        String sLocationPath = this.getClass().getClassLoader().getResource("").getPath()+location;
        //URL url  =this.getClass().getClassLoader().getResource("/"+location);
        System.out.println("mapper location : "+sLocationPath);
        File dir = new File(sLocationPath);
        for (File file : dir.listFiles()) {
            if(file.isDirectory()){
                //递归扫描
                scanLocation(location+"/"+file.getName());
            }else{
                //解析mapper文件
                buildMapperNodes(location+"/"+file.getName());
            }
        }
    }

    private Map<String, MapperNode> buildMapperNodes(String filePath) {
        System.out.println(filePath);
        SAXReader saxReader=new SAXReader();
        URL xmlPath=this.getClass().getClassLoader().getResource(filePath);
        try {
            Document document = saxReader.read(xmlPath);
            Element rootElement=document.getRootElement();

            String namespace = rootElement.attributeValue("namespace");

            Iterator<Element> nodes = rootElement.elementIterator();;
            while (nodes.hasNext()) {   //对每一个sql语句进行解析
                Element node = nodes.next();
                String id = node.attributeValue("id");
                String parameterType = node.attributeValue("parameterType");
                String resultType = node.attributeValue("resultType");
                String sql = node.getText();

                MapperNode selectnode = new MapperNode();
                selectnode.setNamespace(namespace);
                selectnode.setId(id);
                selectnode.setParameterType(parameterType);
                selectnode.setResultType(resultType);
                selectnode.setSql(sql);
                selectnode.setParameter("");

                this.mapperNodeMap.put(namespace + "." + id, selectnode);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this.mapperNodeMap;
    }

    public MapperNode getMapperNode(String name) {
        return this.mapperNodeMap.get(name);
    }
    public String getMapperLocations() {
        return mapperLocations;
    }
    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }
    public Map<String, MapperNode> getMapperNodeMap() {
        return mapperNodeMap;
    }

}
