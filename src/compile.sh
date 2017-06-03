#!/bin/sh

file="Asteroid"

if [ "$1" == "-c" ]; then
	rm *.class
	exit 0
fi

if [ "$1" == "-bo" ]; then
        for f in *.java
        do
                echo "Compiling " $f
                javac $f
        done
        exit 0
fi

echo "Compiling " $file
javac $file.java

if [ $? -eq 0 ]; then
	echo Compiled $file.java without error
else 
	echo Error compiling $file.java
	exit 1
fi

echo "Running " $file
java $file

if [ $? -eq 0 ]; then
	echo Ran $file without error
else 
	echo Error running $file
	exit 1
fi
echo "Finished running"
for file in *.class
do
        echo "Removing $file"
        rm $file
done

