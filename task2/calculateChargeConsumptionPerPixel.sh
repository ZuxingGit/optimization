#!/bin/bash

# Check if the image path argument is missing or if the file doesn't exist
if [ $# -eq 0 ] || [ ! -f "$1" ]; then
    echo "Error: File not found"
    echo "Usage: $0 <image-path>"
    exit 1
fi

# Get the image path from the command line argument
imagePath=$1

# Execute the Java command
java -jar calculateChargeConsumptionPerPixel.jar "$imagePath"
