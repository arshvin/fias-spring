__author__ = 'arshvin'
from xml.etree.ElementTree import iterparse
import sys


def parse(file_name):
    doc = iterparse(file_name, ("end",))

    next(doc) #skip root

    obj_attrib_list = "AOID AOGUID PARENTGUID NEXTID FORMALNAME OFFNAME SHORTNAME REGIONCODE AREACODE " \
                  "POSTALCODE STARTDATE ENDDATE UPDATEDATE".split(" ")

    house_attrib_list = "HOUSEID HOUSEGUID AOGUID HOUSENUM STRSTATUS ESTSTATUS STATSTATUS POSTALCODE " \
                        "STARTDATE ENDDATE UPDATEDATE COUNTER".split(" ")

    attrib_list = lambda tag: obj_attrib_list if tag == "Object" else house_attrib_list

    for event, elem in doc:
        if event == "end":
            line = ["%s:%s"%(key,value) for (key,value) in elem.attrib.items()
                        if key in attrib_list(elem.tag)]
            line.sort()
            yield " ".join(line) + "\n"

i=0
with open(sys.argv[2],mode="wt", buffering=4096) as dst_file:
    for line in parse(sys.argv[1]):
        dst_file.write(line.encode(encoding="utf-8"))
        # i+=1
        # if i > 9:
        #     exit()
