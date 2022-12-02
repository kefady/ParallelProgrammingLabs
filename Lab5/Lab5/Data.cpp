#include <iostream>
#include <mpi.h>
#include <stdio.h>
#include "Data.h"
using namespace std;

int MinValueInArray(int* array, int id)
{
	int min = array[(id - 1) * H];
	for (int i = (id - 1) * H + 1; i < id * H; i++) {
		if (min > array[i]) {
			min = array[i];
		}
	}
	return min;
}

void Calculate(int Zh[H], int X[], int Fh[H], int MA[N][N], int MSh[N][H], int a)
{
	for (int i = 0; i < H; i++) {
		int tmp1 = 0;
		for (int j = 0; j < N; j++) {
			int tmp2 = 0;
			for (int k = 0; k < N; k++) {
				tmp2 += MA[k][j] * MSh[i][k];
			}
			tmp1 += X[j] * tmp2;
		}
		Zh[i] = tmp1 + (a * Fh[i]);
	}
}

int GenerateValue(bool RandomValue)
{
	if (RandomValue) {
		return rand() % 10;
	}
	return 1;
}

void FillVector(int vector[N])
{
	for (int i = 0; i < N; i++) {
		vector[i] = GenerateValue(RandomValue);
	}
}

void FillMatrix(int matrix[N][N])
{
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < N; j++) {
			matrix[i][j] = GenerateValue(RandomValue);
		}
	}
}

char* GetCurrentDate()
{
	const auto now = time(0);
	char* time = ctime(&now);
	time[strlen(time) - 1] = '\0';
	return time;
}