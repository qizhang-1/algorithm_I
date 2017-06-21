JC=javac-algs4
CLASSES=\
	*.java 
JFLAGS=-g
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	rm  *.class
zip:
	zip -r collinear.zip FastCollinearPoints.java BruteCollinearPoints.java Point.java
