
package com.song.generator;

import java.io.IOException;

/**
 * @author soqiho@126.com
 * @version 1.0
 */
public class MapperMaker {
    

    private ParserInfo parserInfo;

    public MapperMaker(ParserInfo parserInfo) {
        this.parserInfo = parserInfo;
    }


    public void makeInterface() throws Exception, IOException {
        Utils.writeContentToFile(
                Utils.createFile(parserInfo.getDaoPackage(),
                        parserInfo.getDaoName(), ".java"),
                Utils.generateDaoContent(parserInfo, "MapperTemplate.ftl"));
        
    }


}
