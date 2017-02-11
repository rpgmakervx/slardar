package org.easyarch.slardar.mapping;

import org.easyarch.slardar.parser.XmlParser;
import org.easyarch.slardar.session.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Reader;

/**
 * Description :
 * Created by xingtianyu on 17-2-9
 * 上午1:02
 * description:
 */

public class MapperInitializer {

    public void initial(InputStream is) throws Exception {
        XmlParser xmlParser = new XmlParser(is);
        Configuration configuration = xmlParser.parse();
        MapperScanner scanner = new MapperScanner();
        scanner.scan(configuration.getPacakge());
    }

    public void initial(Reader reader) throws Exception {
        XmlParser xmlParser = new XmlParser(reader);
        Configuration configuration = xmlParser.parse();
        MapperScanner scanner = new MapperScanner();
        scanner.scan(configuration.getPacakge());
    }

    public void initial(File file) throws Exception {
        if (!file.exists()){
            throw new FileNotFoundException("configuration file not found");
        }
        XmlParser xmlParser = new XmlParser(file.getPath());
        Configuration configuration = xmlParser.parse();
        MapperScanner scanner = new MapperScanner();
        scanner.scan(configuration.getPacakge());
    }
}
