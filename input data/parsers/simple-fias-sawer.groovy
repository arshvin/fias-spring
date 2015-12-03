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

level = 3
counter = 100
hierchyLevel = [1:new HashSet()]

putToHierchy = {aoGuid, parentGuid ->
    levels = hierchyLevel.collect{key,value -> key}.max()
    if (!parentGuid) {//first level of hierchy
        hierchyLevel[1].add(aoGuid)
        println "aoGuid $aoGuid added at level 1"
        1
    } else {
        result = 0
        for(l=1;l<levels+1;l++)each{
            if (hierchyLevel[l].contains(parentGuid)){
                if (!hierchyLevel.containsKey(l+1)){
                    hierchyLevel[l+1] = new HashSet()
                }
                hierchyLevel[l+1].add(aoGuid)
                println "aoGuid $aoGuid added at level ${l+1}"

                result = l+1
            }
        }
        result
    }
}

output.withWriter{ Writer writer ->

    if (xmlStreamReader.getEventType() == START_DOCUMENT) {
        writer << "<?xml version=${xmlStreamReader.getVersion()} encoding=${xmlStreamReader.getEncoding()}?>\n"
    }
    xmlStreamReader.nextTag()
    container = xmlStreamReader.getLocalName()
    writer << "<$container>\n"
    xmlStreamReader.nextTag()

    while (xmlStreamReader.getEventType() != END_DOCUMENT){
        if (xmlStreamReader.getEventType() == START_ELEMENT){
            StringBuilder stringBuilder = new StringBuilder()
            stringBuilder.append("<${xmlStreamReader.getLocalName()} ")

            aoGuid = parentGuid = ""
            for (i=0; i < xmlStreamReader.getAttributeCount(); i++){
                stringBuilder.append("${xmlStreamReader.getAttributeLocalName(i)}=\"${xmlStreamReader.getAttributeValue(i)}\" ")

                switch (xmlStreamReader.getAttributeLocalName(i).toUpperCase()){
                    case "PARENTGUID":
                        parentGuid = xmlStreamReader.getAttributeValue(i)
                        break
                    case "AOGUID":
                        aoGuid = xmlStreamReader.getAttributeValue(i)
                }
            }

            currentLevel = putToHierchy(aoGuid,parentGuid)
            if (currentLevel > 0){
                stringBuilder.append("/>\n")
                writer << stringBuilder.toString()

                if (currentLevel == level){
                    counter--
                }
            }
        } else if (xmlStreamReader.getEventType() == END_ELEMENT){
            writer << "</${xmlStreamReader.getLocalName()}>\n"
        }

        xmlStreamReader.nextTag()
        xmlStreamReader.nextTag()

        if (counter == 0){
            writer << "</$container>\n"
            break
        }
    }

}