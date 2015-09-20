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

enum typeData {AddrObj, HouseObj}
tagsToType = ["AddressObjects":typeData.AddrObj, "Houses":typeData.HouseObj]
xmlStreamReader.
xmlStreamReader.nextTag()

tagsToType.keySet().each{tag ->
    try {
        xmlStreamReader.require(XMLStreamConstants.START_ELEMENT, null, tag )
        xmlType = tagsToType[tag]
    } catch (XMLStreamException exception){}
}
xmlStreamReader.nextTag()
filterClosure = {strBuilder, attr ->
    strBuilder.append("$attr:${xmlStreamReader.getAttributeValue(null, attr)} ") }

while (xmlStreamReader.getEventType() == XMLStreamConstants.START_ELEMENT){
    StringBuilder outLine = new StringBuilder()
    closure = filterClosure.curry(outLine)
    switch (xmlType){
        case typeData.AddrObj:
            objAttribList.each closure
            break
        case typeData.HouseObj:
            houseAttribList.each closure
            break
    }

    while (xmlStreamReader.getEventType() != XMLStreamConstants.END_ELEMENT) {
        xmlStreamReader.next()
    }
    output <<"${outLine.toString()}\n"
    xmlStreamReader.nextTag()
}