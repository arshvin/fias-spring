import groovy.util.slurpersupport.GPathResult

import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLStreamConstants
import javax.xml.stream.XMLStreamException
import javax.xml.stream.XMLStreamReader

/**
 * Created by arshvin on 06.09.15.
 */

def objAttribList = ("AOID AOGUID PARENTGUID NEXTID FORMALNAME OFFNAME SHORTNAME REGIONCODE AREACODE " +
                  "POSTALCODE STARTDATE ENDDATE UPDATEDATE").split(" ")

def houseAttribList = ("HOUSEID HOUSEGUID AOGUID HOUSENUM STRSTATUS ESTSTATUS STATSTATUS POSTALCODE " +
                        "STARTDATE ENDDATE UPDATEDATE COUNTER").split(" ")

def fields = "AOID AOGUID PARENTGUID NEXTID FORMALNAME OFFNAME POSTALCODE ".split(" ")
File input = new File(args[0])
File output = new File(args[1])

XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance()
XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new FileReader(input))

xmlStreamReader.nextTag()

output.withWriter{ Writer writer ->
    while (xmlStreamReader.getEventType() != XMLStreamConstants.END_DOCUMENT){
        if (xmlStreamReader.getEventType() == XMLStreamConstants.START_ELEMENT){
            def outLine = new StringBuilder()

            0.upto(xmlStreamReader.getAttributeCount()){index ->
                if (xmlStreamReader.getAttributeLocalName(index) in fields){
                    outLine.append("${xmlStreamReader.getAttributeLocalName(index)}:${xmlStreamReader.getAttributeValue(index)} ")
                }
            }
            println outLine.toString()
            writer <<"${outLine.toString()}\n"
        }

        xmlStreamReader.nextTag()
    }
}