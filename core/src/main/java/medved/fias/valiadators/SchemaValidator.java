package medved.fias.valiadators;

import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * Created by arshvin on 24.06.16.
 */
public interface SchemaValidator {
    public boolean examine(String schemaFileName, String validatedFileName) throws SAXException, IOException;
}
