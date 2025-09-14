import time
def killprogram():
	print("Death!")
	time.sleep(0.01)
	while True:
		killprogram()

killprogram()

