## This script encrypts a set of loaded files inside a folder, provided via argument for the user
## Written by Victor, adapted from the script by Dennis Gilbert (sekondus)


from Crypto import Random
from Crypto.Cipher import AES
from sys import argv
import binascii
import base64
import sys
import os

# the block size for the cipher object; must be 16, 24, or 32 for AES
# Quick Math: key size of 128, 192 or 256 bits (16, 24 or 32 bytes)
BLOCK_SIZE = 32

# generate a random secret key
# secret = os.urandom(BLOCK_SIZE)
secret = Random.new().read(AES.block_size)

directory=sys.argv[1]
absDirectory = os.path.abspath(directory)

newpath = absDirectory + "/Encrypted"
print(newpath)
if not os.path.exists(newpath):
	os.makedirs(newpath)

files = (file for file in os.listdir(absDirectory) 
         if os.path.isfile(os.path.join(absDirectory, file)))

for filename in files:
	print(filename)
	os.chdir(absDirectory)
	txt = open(filename)
	
	# parse the file to a readable string
	with open(filename) as textfile:
		textContent=textfile.read()
	
	# the character used for padding--with a block cipher such as AES, the value
	# you encrypt must be a multiple of BLOCK_SIZE in length.  This character is
	# used to ensure that your value is always a multiple of BLOCK_SIZE
	PADDING = '{'
	
	# one-liner to sufficiently pad the text to be encrypted
	pad = lambda s: s + (BLOCK_SIZE - len(s) % BLOCK_SIZE) * PADDING
	
	# one-liners to encrypt/encode and decrypt/decode a string
	# encrypt with AES, encode with base64
	EncodeAES = lambda c, s: base64.b64encode(c.encrypt(pad(s)))
	DecodeAES = lambda c, e: c.decrypt(base64.b64decode(e)).rstrip(PADDING)
	
	# create a cipher object using the random secret
	cipher = AES.new(secret)
	
	# encode a string
	encoded = EncodeAES(cipher, textContent)
	#print 'Encrypted string:', encoded
	
	# decode the encoded string
	decoded = DecodeAES(cipher, encoded)
	#print
	#print 'Decrypted string:', decoded
	
	# Outputs the encoded string to a file without extension
	os.chdir(newpath)
	txtOut = open(filename + "En", "w")
	txtOut.write(encoded)
	txtOut.close()
	os.chdir(absDirectory)
	
	# print the key
	# print
	# print 'Key:', binascii.hexlify(secret)
