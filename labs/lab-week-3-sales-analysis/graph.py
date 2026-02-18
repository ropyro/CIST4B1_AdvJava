# references because I'm not super experienced with python:
# matplotlib + pandas + seaborn
# https://www.geeksforgeeks.org/data-visualization/data-visualization-with-python/
# https://matplotlib.org/stable/gallery/subplots_axes_and_figures/subplots_demo.html
# https://matplotlib.org/stable/api/_as_gen/matplotlib.pyplot.savefig.html
# https://stackoverflow.com/questions/9622163/save-plot-to-image-file-instead-of-displaying-it
# taking in cli arguments
# https://stackoverflow.com/questions/4033723/how-do-i-access-command-line-arguments
# file path stuff
# https://stackoverflow.com/questions/59487696/check-if-a-folder-exists-in-a-given-path-and-if-not-then-create-a-folder-there

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

# Create graphs & save
sns.lineplot(x="record_count", y="load_data", data=data)
plt.title('Load CSV Data Graph')
plt.savefig('graphs/load_data.png')
plt.close()

sns.lineplot(x="record_count", y="retrieve_latest", data=data)
plt.title('Retrieve Latest Sale Graph')
plt.savefig('graphs/retrieve_latest.png')
plt.close()

sns.lineplot(x="record_count", y="calculate_total_rev", data=data)
plt.title('Calculate Total Revenue Graph')
plt.savefig('graphs/calculate_total_rev.png')
plt.close()

sns.lineplot(x="record_count", y="check_dupes_for_loops", data=data)
plt.title('Check Duplicates (Nested Loops) Graph')
plt.savefig('graphs/check_dupes_for_loops.png')
plt.close()

sns.lineplot(x="record_count", y="check_dupes_hashmap", data=data)
plt.title('Check Duplicates (Map + Loop) Graph')
plt.savefig('graphs/check_dupes_hashmap.png')
plt.close()

sns.lineplot(x="record_count", y="search_by_id", data=data)
plt.title('Search by Id Graph')
plt.savefig('graphs/search_by_id.png')
plt.close()
