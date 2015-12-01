import groovy.util.slurpersupport.GPathResult

import javax.xml.stream.XMLInputFactory
import static javax.xml.stream.XMLStreamConstants.*
import javax.xml.stream.XMLStreamReader

/**
 * Created by arshvin on 30.11.15.
 */

File input = new File(args[0])
File output = new File(args[1])

XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance()
XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new FileInputStream(input), "utf-8")

output.withWriter{ Writer writer ->
    if (xmlStreamReader.getEventType() == START_DOCUMENT) {
        writer << "<?xml version=${xmlStreamReader.getVersion()} encoding=${xmlStreamReader.getEncoding()}?>\n"
    }
    xmlStreamReader.nextTag()
    container = xmlStreamReader.getLocalName()
    writer << "<$container>\n"
    xmlStreamReader.nextTag()
    ii=0
    while (xmlStreamReader.getEventType() != END_DOCUMENT){
        StringBuilder stringBuilder = new StringBuilder()
        if (xmlStreamReader.getEventType() == START_ELEMENT){
            stringBuilder.append("<${xmlStreamReader.getLocalName()} ")

            for (i=0; i < xmlStreamReader.getAttributeCount(); i++){
                stringBuilder.append("${xmlStreamReader.getAttributeLocalName(i)}=\"${xmlStreamReader.getAttributeValue(i)}\" ")
            }

            stringBuilder.append("/>\n")
            xmlStreamReader.nextTag()
        }
        writer << stringBuilder.toString()
        xmlStreamReader.nextTag()

        if (ii<-1){
            writer << "<$container/>\n"
            break
        } else {
            ii++
        }
    }

}