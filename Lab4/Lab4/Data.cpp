#include <iostream>
#include <stdio.h>
#include <omp.h>
#include "Data.h"
using namespace std;

int H, N, P;
int a, b, e, p;
int *A, *B;
int **MB, **MR, **MZ;
bool RandomValue;
bool isFirsCompare = true;

int MinValueInArray(int* array, int lenth)
{
	int min = array[0];
	for (int i = 0; i < lenth - 1; i++)
	{
		if (min > array[i])
		{
			min = array[i];
		}
	}
	return min;
}

int Calculate(int p, int* A, int* B, int** MB, int** MR, int** MZ, int length, int id, int H)
{
	int result = 0;
	for (int i = (id - 1) * H; i < id * H; i++)
	{
		for (int j = 0; j < length; j++)
		{
			int temp1 = 0;
			int temp2 = 0;
			for (int k = 0; k < length; k++)
			{
				temp1 += MR[i][k] * MZ[k][j];
				temp2 += A[k] * MB[i][k];
			}
			result += p * temp2 * B[j] * temp1;
		}
	}
	return result;
}

int GenerateValue(bool RandomValue)
{
	if (RandomValue)
	{
		return rand() % 10;
	}
	return 1;
}

int* GenerateVector(int length, bool RandomValue)
{
	int* vector = new int[length];
	for (int i = 0; i < length; i++)
	{
		vector[i] = GenerateValue(RandomValue);
	}
	return vector;
}

int** GenerateMatrix(int length, bool RandomValue)
{
	int** matrix = new int* [length];
	for (int i = 0; i < length; i++)
	{
		matrix[i] = new int[length];
		for (int j = 0; j < length; j++)
		{
			matrix[i][j] = GenerateValue(RandomValue);
		}
	}
	return matrix;
}

int InputArrayLength()
{
	int value;
	cout << "Input N: ";
	cin >> value;
	while (true)
	{
		if (cin.fail() || value <= 1)
		{
			cin.clear();
			cin.ignore(numeric_limits<streamsize>::max(), '\n');
			cout << "Wrong value. Try again: ";
			cin >> value;
		}
		else
		{
			break;
		}
	}
	return value;
}

char* GetCurrentDate()
{
	const auto now = time(0);
	char* time = ctime(&now);
	time[strlen(time) - 1] = '\0';
	return time;
}