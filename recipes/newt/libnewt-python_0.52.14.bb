require recipes-extended/newt/libnewt_${PV}.bb

SUMMARY .= " - python"
DEPENDS = "libnewt python"
RDEPENDS_${PN} += "python-core"

inherit pythonnative python-dir

EXTRA_OECONF += "--with-python"
EXTRA_OEMAKE += "PYTHONVERS=${PYTHON_DIR}"


do_compile () {
	VERSION="$(sed -n 's/^VERSION = //p' Makefile)"
	oe_runmake "LIBNEWTSH=${STAGING_LIBDIR}/libnewt.so.$VERSION" _snackmodule.so
}

do_install () {
	install -d ${D}${PYTHON_SITEPACKAGES_DIR}
	install -m 0755 ${PYTHON_DIR}/_snackmodule.so ${D}${PYTHON_SITEPACKAGES_DIR}/
	install -m 0644 snack.py ${D}${PYTHON_SITEPACKAGES_DIR}/
}


FILES_${PN} = "${PYTHON_SITEPACKAGES_DIR}/*"
FILES_${PN}-dbg += "${PYTHON_SITEPACKAGES_DIR}/.debug/"
