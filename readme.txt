====================
Affine Cipher
====================

The program can do both encryption and decryption. Lowercase characters a-z will be encrypted/decrypted to lowercase
characters. Uppercase characters A-Z will be encrypted/decrypted to uppercase characters. Other characters not in the range
(a-z, A-Z) will be unchanged.

The program can accept the following parameters: 
= flag to indicate encryption or decryption
= secret key
= input file name
= output file name

For example:
= myProgram -key 3 8 -encrypt -in file1.txt -out file2.txt
= myProgram -key 3 8 -decrypt -in file2.txt -out file3.txt

Algorithm:
C = aM + b (mod 26)
where
= C, M, b ∈ {0,1,2,…,24,25}
= a ∈ {1,3,5,7,9,11,15,17,19,21,23,25}
= M is a plaintext (message) character
= C is the corresponding ciphertext character

