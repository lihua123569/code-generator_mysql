
package com.song.generator;


/**
 * @author soqiho@126.com
 * @version 1.0
 */
public class XmlMaker {
    
    private ParserInfo parserInfo;

    public XmlMaker(ParserInfo parserInfo) {
        this.parserInfo = parserInfo;
    }

    public void makeDao(String tableName) throws Exception {
        Utils.writeContentToFile(
                Utils.createFile(parserInfo.getDaoPackage(),
                        parserInfo.getDaoName(), ".xml"),
                Utils.generateDaoContent(parserInfo, "TemplateMapper.ftl"));
    }

}
