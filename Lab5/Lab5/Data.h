#pragma warning(disable : 4996)
#pragma once
#ifndef DATA
#define DATA

const int N = 8;
const int P = 8;
const int H = N / P;
const bool RandomValue = false;

int MinValueInArray(int* array, int id);
int GenerateValue(bool RandomValue);
void FillVector(int vector[N]);
void FillMatrix(int matrix[N][N]);
char* GetCurrentDate();
void Calculate(int Zh[H], int X[], int Fh[H], int MA[N][N], int MSh[N][H], int a);

#endif