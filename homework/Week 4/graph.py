import matplotlib.pyplot as plt
import seaborn as sns
import pandas as pd
import pathlib
import sys

# import the data from file specified in the cli on start up
if len(sys.argv) < 2:
    print("Usage: python graph.py <path_to_csv>")
    sys.exit(1)

# check for path to graph output
path = pathlib.Path("graphs")
path.mkdir(parents=True, exist_ok=True)

# load csv data
data = pd.read_csv(sys.argv[1])

# convert nanoseconds to milliseconds
data["time_ms"] = data["time_to_sort"] / 1_000_000

# Create graph & save
sns.lineplot(x="array_length", y="time_ms", data=data)
plt.title('Merge Sort Performance')
plt.xlabel('Array Length')
plt.ylabel('Time to Sort (ms)')
plt.savefig('graphs/merge_sort.png')
plt.close()