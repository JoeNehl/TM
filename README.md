# TM Bug Update
There is a bug with the way description is printed. If the task is started before the description is put in, it will display the 
description upon output one space below the line. I have figured out why this bug is happeneing (the way i initilize the description and
have the description update) but i cannot think of a way to fix it without having extra lines of code that arent in the summary (which 
i want to avoid doing becuase this makes the code more complex).

# TM Update2
Added in a function before the data is displayed that makes the times easier to read.
Instead of just showing seconds, it now properly displays the time in hrs:min:sec.
This is just a small update to help improve user readablitly of the summary.

# TM Update
This week I added in the functionality to classify a task size and write in a longer description.
This weeks assingment was much easier bucause it was bulding off code that i feel is pretty opptimized.
I added in one very quick change by adding in a very simple function called size.
It takes the 3rd arg and converts it to size.
The other change was a little more difficult but just because of one bug.
The actual functionality was very easy to add in, it just saves it instead of replacing it and seperates by a new line.
the bug was that The first description would always print one line down from the description line.
I fixed this by changeing the order of my if statement although nothing worked for a while.
Overall these fixes and changes were relitivly easy to implement
