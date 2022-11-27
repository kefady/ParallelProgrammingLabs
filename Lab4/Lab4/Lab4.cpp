/**
 * Паралельне програмування
 * Лабораторна робота #4
 * Варіант: 11
 * Завдання: e = ((p * (A * MB)) * (B * (MZ * MR)) + min(B)
 * ПВВ1: MZ
 * ПВВ2: e, A, MR
 * ПВВ3: p, B, MB
 * Ващук Кирил ІО-02
 * Дата 26.11.2022
**/

#include <iostream>
#include <stdio.h>
#include <chrono>
#include <omp.h>
#include "Data.h"
using namespace std;

int main()
{
	printf("Lab4 -> %s: is started.\n", GetCurrentDate());
	N = InputArrayLength();
	P = 4;
	H = N / P;
	RandomValue = false;
	auto start = chrono::high_resolution_clock::now();
	#pragma omp parallel num_threads(P) shared(a, b, A, B, MZ)
	{
		srand(time(0));
		int id = omp_get_thread_num() + 1;
		printf("T%d -> %s: is started.\n", id, GetCurrentDate());
		if (id == 1) // T1
		{
			MZ = GenerateMatrix(N, RandomValue); // Введення MZ
		}
		if (id == 2) // T2
		{
			A = GenerateVector(N, RandomValue); // Введення A
			MR = GenerateMatrix(N, RandomValue); // Введення MR
		}
		if (id == 3) // T3
		{
			p = GenerateValue(RandomValue); // Введення p
			B = GenerateVector(N, RandomValue); // Введення B
			MB = GenerateMatrix(N, RandomValue); // Введення MB
		}
		printf("T%d -> %s: data entry is complete.\n", id, GetCurrentDate());
		#pragma omp barrier // Чекати, поки всі потоки завершать ввід даних
		int ai = MinValueInArray(B, N); // Обчислення 1: ai = min⁡(Bh)
		printf("T%d -> %s: calculation 1 is complete.\n", id, GetCurrentDate());
		#pragma omp critical (CS1)
		{
			if (isFirsCompare)
			{
				a = ai;
				isFirsCompare = false;
			}
			else
			{
				a = min(a, ai); // Обчислення 2: a = min⁡(a, ai)
			}
		}
		printf("T%d -> %s: calculation 2 is complete.\n", id, GetCurrentDate());
		int pi;
		#pragma omp critical (CS3)
		{
			pi = p; // Копія pi = p
		}
		printf("T%d -> %s: 'p' is copied.\n", id, GetCurrentDate());
		int bi = Calculate(pi, A, B, MB, MR, MZ, N, id, H);
		printf("T%d -> %s: calculation 3 is complete.\n", id, GetCurrentDate());
		#pragma omp critical (CS2)
		{
			b = b + bi; // Обчислення 4: b = b + bi
		}
		printf("T%d -> %s: calculation 4 is complete.\n", id, GetCurrentDate());
		#pragma omp barrier // Чекати, поки всі потоки завершать обчислення
		if (id == 2) // T2
		{
			e = b + a; // Обчислення 5: e = b + a
			printf("T%d -> %s: calculation 5 is complete.\n", id, GetCurrentDate());
			printf("T%d -> %s:\033[32m e = %d\033[0m.\n", id, GetCurrentDate(), e);
		}
		printf("T%d -> %s: is finished.\n", id, GetCurrentDate());
	}
	auto end = chrono::high_resolution_clock::now();
	auto executionTime = chrono::duration <double, milli>(end - start).count();
	printf("Lab4 -> %s: is finished.\n", GetCurrentDate());
	printf("Execution time %f ms.\n", executionTime);
	system("pause");
	return 0;
}