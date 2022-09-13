import xmlplain
import codecs
# Read to plain object
with codecs.open(r'WednesdayXML.txt', "r", "utf_8_sig") as inf:
  root = xmlplain.xml_to_obj(inf, strip_space=True, fold_dict=True)

# Output plain YAML
with codecs.open(r'WednesdayYAML.txt', "w") as outf:
  xmlplain.obj_to_yaml(root, outf)
