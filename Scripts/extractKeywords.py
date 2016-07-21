#
## This script extracts metadata from text files
### Written by Victor
##
#

from sys import argv
import nltk
import hashlib
from collections import Counter
from nltk.corpus import stopwords

hex_dig=[] #Keeps the words in SHA256 format
hashFrequency=[] #Keeps the frequency of the words as they appear in the text

# load text file
script, filename = argv
txt = open(filename)

# Uncoment this line in case of the script is running
# on a new computer. NLTK will need to download all necessary libraries 
# in order to be able to run. The process might take a while
#nltk.download('all')

# Parse the document to an object
with open(filename) as textfile:
        textContent=textfile.read()

# Tokenize the document
tokens = nltk.word_tokenize(textContent)

# Load the stopwords dictionary
stopwords = stopwords.words('english')

# Where the magic happens.
# For each token that is not on the dictionary of stopwords
filteredWords = [token for token in tokens if token not in stopwords]

# Parse to the termFrequency object the frequency of the filtered words
termFrequency = Counter(filteredWords)

# Let's convert the metadata words to SHA-256
for key, value in termFrequency.items():
        hash_object = hashlib.sha256(str(key))
        hex_dig.append(hash_object.hexdigest())

# Let's add the metadata to a list, easier to manage
for key, value in termFrequency.items():
	hashFrequency.append(value)

# Writing the values to a file. 
txtOut = open(filename + "Data", "w+")
for key, value in zip(hex_dig, hashFrequency):
	txtOut.write(str(key) + ":" + str(value) + "\n")
txtOut.close()

# -= Debug Options =-
#print("Word Frequency List Size: " + str(len(termFrequency)))
#print("Hash words list size: " + str(len(hex_dig)))
#print("Hash Frequency list size: " + str(len(hashFrequency)))
#print(termFrequency)

