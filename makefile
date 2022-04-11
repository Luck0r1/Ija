
buildDir=build
srcDir=src
libDir=lib
lib=lib/javafx-sdk-18/lib
app = Starter

modules = javafx.controls,javafx.fxml

all: clean test1

clean:
	rm -rf $(libDir)/javafx-sdk-18
	rm -rf $(buildDir)
	mkdir $(buildDir)

test1:
	bash $(libDir)/downloader.sh
	mv javafx-sdk-18 $(libDir)
	javac -cp $(srcDir) --module-path $(lib) --add-modules $(modules) -d $(buildDir) $(srcDir)/$(app).java
	java -ea -cp $(buildDir) --module-path $(lib) --add-modules $(modules) $(app)

r:
	rm -rf $(buildDir)
	mkdir $(buildDir)
	javac -cp $(srcDir) --module-path $(lib) --add-modules $(modules) -d $(buildDir) $(srcDir)/$(app).java
	java -ea -cp $(buildDir) --module-path $(lib) --add-modules $(modules) $(app)



