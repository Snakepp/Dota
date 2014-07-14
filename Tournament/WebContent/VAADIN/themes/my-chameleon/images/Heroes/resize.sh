#!/bin/bash
for img in *.jpg; do
	convert "$img" -resize 50% icons/$(basename "$img" .jpg).jpg 
done
exit
