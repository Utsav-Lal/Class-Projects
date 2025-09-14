import os
import time

																		   #outputs should make more sense as we go down the list
tests = ["0 10   ../../inputs/input.txt     studentOut/randomOut.txt",     #does the program handle entirely random
		 "8 50   ../../inputs/input.txt     studentOut/goesBeyondEnd.txt", #Randomly chooses a seed when it goes beyond the end of the file?
		 "5 200  ../../inputs/seuss.txt     studentOut/drGibberish.txt",   #regular functioning
		 "7 500  ../../inputs/alice.txt     studentOut/longer.txt",        #Long input file - runtime test < 5 sec
		 "9 1000 ../../inputs/tomsawyer.txt studentOut/veryLong.txt"]      #VERY long input file - runtime test < 15 sec.

print("#######################################################")
print("#######   \tRunning project\t#######")
print("#######################################################\n")

os.system("mkdir studentOut/") #makes student output directory if it doesn't exist
os.system("javac *.java")	#compiles student files

#for file in os.listdir("inputs/"):  #runs for each input file
for file in tests:
	print("*" * 80)
	print("Testing " + file)

	
	#runs and tests differences between student output and expected output
	start = time.time()
	try:
		# os.system("rm " + file.split()[-1])
		os.system("java RandomWriter " + file)
	except:
		print("Error in running", file)
	else:
		print("runtime: " + str(round(time.time()-start,2)) + " sec.")
		
		try:
			numChars = len(open((file.split()[-1]), "r").read())
		except:
			print("error with fetching output file")
	
			
		if numChars != int(file.split()[1]):   # Did the program write the correct number of chars?
			print("File length is not as expected:")
			print("\tExpected:", file.split()[1], ", Actual:", numChars)
		else:
			print("Correct file length!")
		
		os.system("cat " + file.split()[-1])  # Contents of generated file
	
		
	print("\n")
	input()
	
print("Completed")
print("#######################################################\n")