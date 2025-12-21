# Java-Hill-Cipher-Encoder-Decoder
A Java implementation of the **Hill Cipher**, a polyalphabetic substitution cipher based on linear algebra. This project uses matrix multiplication and modular arithmetic to encrypt and decrypt text.

# Features
- **Matrix-Based Encryption:** Uses a 2x2 invertible matrix for encryption, but can perform the cipher using any valid nxn matrix.
- **Modular Arithmetic:** Implements calculations under mod 26.
- **Dynamic Padding:** Automatically pads messages to fit matrix dimensions.
- **Decryption Logic:** Calculates the Modular Multiplicative Inverse of the determinant to perfectly reverse the encryption.

# How It Works
**Encryption** ->
The plaintext is converted into vectors of length n. Each vector E is multiplied by an nxn key matrix A:
D = (A.E) [mod(26)]

**Decryption** ->
To recover the message, the ciphertext vector D is multiplied by the inverse of the key matrix:
E = (A^(-1).D) [mod(26)]

# Important Note on Key Matrices
For a matrix to be valid for decryption in a Hill Cipher, its determinant must be coprime to 26. This means **gcd(det(A), 26) = 1**. Common valid determinants: 1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25.
