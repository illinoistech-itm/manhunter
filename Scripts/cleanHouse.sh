#!bin/bash

 # This script will clean all the buckets from S3Storage
 # based on the inputs given by the user
 # author: Paulo Victor && Jhonatan

echo -n "Insert the name or part of the name of the folders you want to clean: "
read input
echo -n "Confirm? [Y/N]: "
read confirm

confirm="$(echo ${confirm} | tr 'A-Z' 'a-z')"

if [ X"$confirm" = X"y" ]; then
 echo "All right. Looking for folders named $input and deleting the content of the buckets..."
 ./s3cmd --signature-v2 ls | grep s3://$input | awk '{print $3"/*"}' | xargs ./s3cmd --signature-v2 del
 echo "The buckets are clean. Now we can proceed to complete deletion of the empty buckets. Doing it now.."
 ./s3cmd --signature-v2 ls | grep s3://$input | awk '{print $3""}' | xargs ./s3cmd --signature-v2 rb
 echo "All buckets deleated."
else
 echo "Exiting..."
fi
