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
    fig, axs = plt.subplots(1, 3, figsize=(12, 4))

    axs[0].boxplot(data[0][i])
    axs[0].set_title('RandomSearch')
    axs[0].scatter([1 for i in range(10)],data[0][i], color='red', alpha=0.3)

    axs[1].boxplot(data[1][i])
    axs[1].set_title('HillClimbing')
    axs[1].scatter([1 for i in range(10)],data[1][i], color='red', alpha=0.3)

    axs[2].boxplot(data[2][i])
    axs[2].set_title('Simulated Annealing')
    axs[2].scatter([1 for i in range(10)],data[2][i], color='red', alpha=0.3)

    fig.suptitle('Fitness distribution of 3 algorithms')

    plt.tight_layout()
    num_of_shots = pow(10,i+1)
    plt.savefig(f'box_plot_combination_cal{num_of_shots}.png')


for i in range(3):
    bxplot(data,i)
