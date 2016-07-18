#
## This script extracts metadata from text files
### Written by Victor
##
#

from sys import argv
import nltk
from collections import Counter
from nltk.corpus import stopwords

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

txtOut = open(filename + "Data", "w")

for key, value in termFrequency.items():
	txtOut.write(str(key) + ":" + str(value) + "\n")
#	print(key,value)

txtOut.close()
