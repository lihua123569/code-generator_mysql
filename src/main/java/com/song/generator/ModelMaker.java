
package com.song.generator;

import java.io.IOException;

/**
 * @author soqiho@126.com
 * @version 1.0
 */
public class ModelMaker {
    

    private ParserInfo parserInfo;

    public ModelMaker(ParserInfo parserInfo) {
        this.parserInfo = parserInfo;
    }

    public void makeModel() throws IOException, Exception {
        Utils.writeContentToFile(
                Utils.createFile(parserInfo.getModelPackage(),
                        parserInfo.getModelName(), ".java"),
                Utils.generateDaoContent(parserInfo, "ModelTemplate.ftl"));
    }

}
