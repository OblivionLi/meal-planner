# meal-planner

#### The meal planner app allows users to effortlessly add meals and their corresponding ingredients, which are then stored in a database and presented in an aesthetically pleasing format. Furthermore, the app offers the convenient feature of categorizing meals by type for each day of the week, displaying the entire plan afterwards. Additionally, it provides the ability to export a shopping list, containing all the necessary ingredients for the planned meals, into a text file.

##### Examples below might differ (they are from different stages in development); first example is from the last stage of development.
---
##### Example (`>` is user input):
---
```
What would you like to do (add, show, plan, exit)?
> plan
Monday
oatmeal
sandwich
scrambled eggs
yogurt
Choose the breakfast for Monday from the list above:
> yogurt
avocado egg salad
chicken salad
sushi
tomato salad
wraps
Choose the lunch for Monday from the list above:
> tomato salad
beef with broccoli
pesto chicken
pizza
ramen
tomato soup
Choose the dinner for Monday from the list above:
> spaghetti
This meal doesn’t exist. Choose a meal from the list above.
> ramen
Yeah! We planned the meals for Monday.

Tuesday
oatmeal
sandwich
scrambled eggs
yogurt
Choose the breakfast for Tuesday from the list above:
> oatmeal
avocado egg salad
chicken salad
sushi
tomato salad
wraps
Choose the lunch for Tuesday from the list above:
> wraps
beef with broccoli
pesto chicken
pizza
ramen
tomato soup
Choose the dinner for Tuesday from the list above:
> ramen
Yeah! We planned the meals for Tuesday.

Wednesday
oatmeal
sandwich
scrambled eggs
yogurt
Choose the breakfast for Wednesday from the list above:
> sandwich
avocado egg salad
chicken salad
sushi
tomato salad
wraps
Choose the lunch for Wednesday from the list above:
> avocado egg salad
beef with broccoli
pesto chicken
pizza
ramen
tomato soup
Choose the dinner for Wednesday from the list above:
> pesto chicken
Yeah! We planned the meals for Wednesday.

Thursday
oatmeal
sandwich
scrambled eggs
yogurt
Choose the breakfast for Thursday from the list above:
> oatmeal
avocado egg salad
chicken salad
sushi
tomato salad
wraps
Choose the lunch for Thursday from the list above:
> chicken salad
beef with broccoli
pesto chicken
pizza
ramen
tomato soup
Choose the dinner for Thursday from the list above:
> tomato soup
Yeah! We planned the meals for Thursday.

Friday
oatmeal
sandwich
scrambled eggs
yogurt
Choose the breakfast for Friday from the list above:
> yogurt
avocado egg salad
chicken salad
sushi
tomato salad
wraps
Choose the lunch for Friday from the list above:
> sushi
beef with broccoli
pesto chicken
pizza
ramen
tomato soup
Choose the dinner for Friday from the list above:
> pizza
Yeah! We planned the meals for Friday.

Saturday
oatmeal
sandwich
scrambled eggs
yogurt
Choose the breakfast for Saturday from the list above:
> scrambled eggs
avocado egg salad
chicken salad
sushi
tomato salad
wraps
Choose the lunch for Saturday from the list above:
> wraps
beef with broccoli
pesto chicken
pizza
ramen
tomato soup
Choose the dinner for Saturday from the list above:
> pesto chicken
Yeah! We planned the meals for Saturday.

Sunday
oatmeal
sandwich
scrambled eggs
yogurt
Choose the breakfast for Sunday from the list above:
> scrambled eggs
avocado egg salad
chicken salad
sushi
tomato salad
wraps
Choose the lunch for Sunday from the list above:
> tomato salad
beef with broccoli
pesto chicken
pizza
ramen
tomato soup
Choose the dinner for Sunday from the list above:
> beef with broccoli
Yeah! We planned the meals for Sunday.

Monday
Breakfast: yogurt
Lunch: tomato salad
Dinner: ramen

Tuesday
Breakfast: oatmeal
Lunch: wraps
Dinner: ramen

Wednesday
Breakfast: sandwich
Lunch: avocado egg salad
Dinner: pesto chicken

Thursday
Breakfast: oatmeal
Lunch: chicken salad
Dinner: tomato soup

Friday
Breakfast: yogurt
Lunch: sushi
Dinner: pizza

Saturday
Breakfast: scrambled eggs
Lunch: wraps
Dinner: pesto chicken

Sunday
Breakfast: scrambled eggs
Lunch: tomato salad
Dinner: beef with broccoli

What would you like to do (add, show, plan, exit)?
> exit
Bye!
```
##### Example 2 (`>` is user input):
---
```
What would you like to do (add, show, exit)?
> add
Which meal do you want to add (breakfast, lunch, dinner)?
> lunch
Input the meal's name:
> salad
Input the ingredients:
> lettuce, tomato, onion, cheese, olives
The meal has been added!
What would you like to do (add, show, exit)?
> add
Which meal do you want to add (breakfast, lunch, dinner)?
> lunch
Input the meal's name:
> omelette
Input the ingredients:
> eggs, milk, cheese
The meal has been added!
What would you like to do (add, show, exit)?
> add
Which meal do you want to add (breakfast, lunch, dinner)?
> breakfast
Input the meal's name:
> oatmeal
Input the ingredients:
> oats, milk, banana, peanut butter
The meal has been added!
What would you like to do (add, show, exit)?
> show
Which category do you want to print (breakfast, lunch, dinner)?
> breakfast
Category: breakfast
Name: oatmeal
Ingredients:
oats
milk
banana
peanut butter
What would you like to do (add, show, exit)?
> show
Which category do you want to print (breakfast, lunch, dinner)?
> lunch
Category: lunch

Name: salad
Ingredients:
lettuce
tomato
onion
cheese
olives

Name: omelette
Ingredients:
eggs
milk
cheese

What would you like to do (add, show, exit)?
> exit
Bye!
```










