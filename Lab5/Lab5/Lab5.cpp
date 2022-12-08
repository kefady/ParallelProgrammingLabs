/**
 * Паралельне програмування
 * Лабораторна робота #5
 * Варіант: 8
 * Завдання: Z = X * (MA * MS) + min(X) * F
 * ПВВ1: MA
 * ПВВ2: X
 * ПВВ3: MS
 * ПВВ4: Z
 * ПВВ8: F
 * Ващук Кирил ІО-02
 * Дата 02.12.2022
**/

#include <iostream>
#include <mpi.h>
#include <stdio.h>
#include "Data.h"
using namespace std;

double startTime, endTime;
int nodes = 8;
int index[8] = { 1, 2, 3, 10, 11, 12, 13, 14 };
int edges[14] = { 3, 3, 3, 0, 1, 2, 4, 5, 6, 7, 3, 3, 3, 3 };
int a, ai, F[N], Fh[H], X[N], Z[N], Zh[H], MA[N][N], MS[N][N], MSh[N][H];
MPI_Comm graph;

int main(int argc, char* argv[])
{
	srand(time(0));
	MPI_Init(&argc, &argv);
	MPI_Graph_create(MPI_COMM_WORLD, nodes, index, edges, false, &graph);
	int rank;
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	int id = rank + 1;
	printf("T%d -> %s: is started.\n", id, GetCurrentDate());
	startTime = MPI_Wtime();

	if (id == 1) { // T1
		FillMatrix(MA); // Введення MA
	}
	if (id == 2) { // T2
		FillVector(X); // Введення X
	}
	if (id == 3) { // T3
		FillMatrix(MS); // Введення MS
	}
	if (id == 8) { // T8
		FillVector(F); // Введення F
	}

	MPI_Bcast(&X, N, MPI_INT, 1, graph); // Передати та прийняти X
	MPI_Bcast(&MA, N*N, MPI_INT, 0, graph); // Передати та прийняти MA
	MPI_Scatter(&F, H, MPI_INT, &Fh, H, MPI_INT, 7, graph); // Передати та прийняти F
	MPI_Scatter(&MS, N*H, MPI_INT, &MSh, N*H, MPI_INT, 2, graph); // Передати та прийняти MS

	ai = MinValueInArray(X, id); // Обчислення 1: a = min(Xh)

	MPI_Reduce(&ai, &a, 1, MPI_INT, MPI_MIN, 3, graph); // Передати ai процесу T4 (Обчислення 2)

	MPI_Bcast(&a, 1, MPI_INT, 3, graph); // Передати a всім процесам групи graph

	Calculate(Zh, X, Fh, MA, MSh, a); // Обчислення 3: Zh = X * (MA * MSh) + ai * Fh

	MPI_Gather(&Zh, H, MPI_INT, &Z, H, MPI_INT, 3, graph); // Передати Zh процесу T4

	if (id == 4) { // T4
		printf("T%d -> %s: \033[32mZ = [", id, GetCurrentDate());
		for (int i = 0; i < N; i++) {
			printf("%d", Z[i]);
			if (i < N - 1) {
				printf(", ");
			}
		}
		printf("]\033[0m.\n"); // Вивести Z
	}

	endTime = MPI_Wtime();
	printf("T%d -> %s: is finished.\n", id, GetCurrentDate());
	if (id == 4) {
		printf("T%d -> %s: execution time \033[32m%f s\033[0m.\n", id, GetCurrentDate(), endTime - startTime);
	}
	MPI_Finalize();
	return 0;
}

