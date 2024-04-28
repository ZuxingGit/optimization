import pandas as pd
import matplotlib.pyplot as plt

algorithms = ["RandomSearch", "HillClimbing", "SimulatedAnnealing"]
location = "screenshots/Calculator/"


for alg in algorithms:
    count = 1
    avg_fitness = []
    for i in range(3):
        count *= 10
        fitness = []
        for j in range(10):
            path = location + alg + "/round" + str(j+1) + "/" + str(count) + ".csv"
            print(path)
            df = pd.read_csv(path)

            val_1 = df.iloc[26, 1]
            fitness.append(val_1)
        avg_fitness.append(sum(fitness) / 10)
    plt.plot(avg_fitness,label=alg)


plt.xticks([0, 1, 2], [10, 100, 1000])
plt.title("Average charge consumption (Calculator)")
plt.xlabel('num of screenshots')
plt.ylabel('charge consumption')
plt.legend()
plt.savefig('line_plot_combination_cal.png')

plt.show()

