import matplotlib.pyplot as plt
import pandas as pd


algorithms = ["RandomSearch", "HillClimbing", "SimulatedAnnealing"]
location = "screenshots/Calculator/"
data = []
for alg in algorithms:
    count = 1
    shot_fitness = []
    for i in range(3):
        count *= 10
        fitness = []
        for j in range(10):
            path = location + alg + "/round" + str(j+1) + "/" + str(count) + ".csv"
            # print(path)
            df = pd.read_csv(path)

            val_1 = df.iloc[26, 1]
            fitness.append(val_1)
        shot_fitness.append(fitness)
    data.append(shot_fitness)


def bxplot(data,i):
    plt.boxplot(data)

    plt.xticks([1, 2, 3], algorithms)

    plt.suptitle('Comparison of fitness for 3 algorithms')

    plt.title('')
    plt.xlabel('Algorithm')
    plt.ylabel('Charge consumption')
    num_of_shots = pow(10, i + 1)
    plt.savefig(f'box_plot_comparison_cal{num_of_shots}.png')


for i in range(3):
    bxplot(data[i],i)
