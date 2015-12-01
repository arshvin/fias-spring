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

File input = new File(args[0])
File output = new File(args[1])
output.text = ""

XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance()
XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new FileReader(input))

xmlStreamReader.nextTag()
xmlStreamReader.nextTag()

while (xmlStreamReader.getEventType() == XMLStreamConstants.START_ELEMENT){
    while (xmlStreamReader.getEventType() != XMLStreamConstants.END_ELEMENT) {
        xmlStreamReader.next()
    }
    output <<"${outLine.toString()}\n"
    xmlStreamReader.nextTag()
}