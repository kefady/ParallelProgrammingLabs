#pragma once
#pragma warning(disable : 4996)
#ifndef DATA
#define DATA

extern int H, N, P;
extern int a, b, e, p;
extern int *A, *B;
extern int **MB, **MR, **MZ;
extern bool RandomValue;
extern bool isFirsCompare;

int InputArrayLength();
int MinValueInArray(int* array, int lenth);
int Calculate(int p, int* A, int* B, int** MB, int** MR, int** MZ, int length, int id, int H);
int GenerateValue(bool RandomValue);
int* GenerateVector(int length, bool RandomValue);
int** GenerateMatrix(int length, bool RandomValue);
char* GetCurrentDate();

#endif