package medved.fias.valiadators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

/**
 * Created by arshvin on 15.10.15.
 */
public class SchemaValidator {
    private String schemaFileName;
    private String validatedFileName;
    private Logger log = LoggerFactory.getLogger(SchemaValidator.class);

    public SchemaValidator(String schemaFileName, String validatedFileName) {
        this.schemaFileName = schemaFileName;
        this.validatedFileName = validatedFileName;
    }

    public boolean examine() throws SAXException, IOException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(new File(schemaFileName));
        Validator validator = schema.newValidator();

        Boolean errors = false;

        ErrorHandler errHandler = new ErrorHandler() {
            public void error( SAXParseException e ){
                log.info(String.valueOf(e));
                e.printStackTrace();
            }
            public void fatalError( SAXParseException e ) {
                log.info(String.valueOf(e));
                e.printStackTrace();
            }
            public void warning( SAXParseException e ) {
                log.info(String.valueOf(e));
            }
        };
        validator.setErrorHandler(errHandler);

        validator.validate( new SAXSource(new InputSource((validatedFileName))));

        return true;
    }
}
