import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

def main():
    # Read the CSV file
    df = pd.read_csv("homework/Week 4/results.csv")

    # Convert nanoseconds to milliseconds for readability
    df["time_ms"] = df["time_to_sort"] / 1_000_000

    # Create the figure and axes
    fig, ax = plt.subplots(figsize=(12, 7))

    # Plot the raw data points
    ax.scatter(
        df["array_length"],
        df["time_ms"],
        color="steelblue",
        alpha=0.5,
        s=10,
        label="Raw Data"
    )

    # Plot a smoothed trend line using a rolling average
    window_size = 10
    df["smoothed"] = df["time_ms"].rolling(window=window_size, center=True).mean()
    ax.plot(
        df["array_length"],
        df["smoothed"],
        color="red",
        linewidth=2,
        label=f"Smoothed (window={window_size})"
    )

    # Plot the theoretical O(n log n) curve, scaled to fit the data
    n = df["array_length"].values
    nlogn = n * np.log2(n)
    # Scale the theoretical curve to match the magnitude of actual data
    scale_factor = df["time_ms"].median() / np.median(nlogn)
    ax.plot(
        n,
        nlogn * scale_factor,
        color="green",
        linewidth=2,
        linestyle="--",
        label="Theoretical O(n log n)"
    )

    # Labels and formatting
    ax.set_title("Merge Sort Performance", fontsize=16, fontweight="bold")
    ax.set_xlabel("Array Length", fontsize=13)
    ax.set_ylabel("Time to Sort (ms)", fontsize=13)
    ax.legend(fontsize=11)
    ax.grid(True, alpha=0.3)
    ax.set_xlim(left=0)
    ax.set_ylim(bottom=0)

    # Add summary statistics as text box
    stats_text = (
        f"Min time: {df['time_ms'].min():.2f} ms\n"
        f"Max time: {df['time_ms'].max():.2f} ms\n"
        f"Mean time: {df['time_ms'].mean():.2f} ms\n"
        f"Data points: {len(df)}"
    )
    ax.text(
        0.02, 0.98, stats_text,
        transform=ax.transAxes,
        fontsize=10,
        verticalalignment="top",
        bbox=dict(boxstyle="round", facecolor="wheat", alpha=0.5)
    )

    plt.tight_layout()

    # Save and show the graph
    plt.savefig("merge_sort_performance.png", dpi=300)
    print("Graph saved to merge_sort_performance.png")
    plt.show()


if __name__ == "__main__":
    main()