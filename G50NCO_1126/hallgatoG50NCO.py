from lxml import etree

# Forrásfájlok
xml_file = "hallgatoG50NCO.xml"
xsl_file = "hallgatoG50NCO.xsl"
output_file = "hallgatoG50NCO.out.xml"

try:
    # XML és XSL betöltése
    xml_tree = etree.parse(xml_file)
    xsl_tree = etree.parse(xsl_file)

    # XSLT transzformáció
    transform = etree.XSLT(xsl_tree)
    result_tree = transform(xml_tree)

    # Az átalakított eredmény írása fájlba
    with open(output_file, "wb") as f:
        f.write(etree.tostring(result_tree, pretty_print=True, encoding="UTF-8"))

    print(f"Sikeresen letrehozva: {output_file}")

except Exception as e:
    print(f"Hiba tortent: {e}")