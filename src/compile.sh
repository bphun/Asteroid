#!/bin/sh

file="Asteroid"

if [ "$1" == "-c" ]; then
	rm *.class
	exit 0
fi

if [ "$1" == "-bo" ]; then
	javac $file.java
	exit 0
fi

echo "Compiling " $file
javac $file.java

echo "Running " $file
java $file

echo "Finished running"

for file in *.class
do
        echo "Removing $file"
        rm $file
done

