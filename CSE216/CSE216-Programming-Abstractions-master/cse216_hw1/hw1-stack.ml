(* Nur Nibir
   111059470 *)
type stack =
| Stack of int list;;

let start f =
	f (Stack([]));;

let push n l f = match l with 
	| Stack(lst) -> f (Stack(n::lst));;

let pop l f = match l with  
	| Stack([]) ->  f (Stack([]))
	| Stack([_]) -> f (Stack([]))
	| Stack(h :: t) -> f (Stack(t));;

let add l f = match l with 
	| Stack([]) -> f (Stack([]))
	| Stack([x]) -> f (Stack([x])) 
	| Stack(a::b::t) -> f (Stack(((a+b)::t)));;

let mult l f = match l with 
	| Stack([]) -> f (Stack([]))
	| Stack([x]) -> f (Stack([x]))
	| Stack(a::b::t) -> f (Stack((a*b)::t));;

let clone l f = match l with  
	| Stack([]) -> f (Stack([]))
	| Stack([x]) -> f (Stack(x :: [x])) 
	| Stack(h :: t) -> f (Stack(h :: h :: t));;

let rec kpop_helper a l = match l with  
	| Stack([]) -> Stack([])
	| Stack(h :: t) -> if(a = 0) then Stack(h :: t) else kpop_helper (a-1) (Stack(t));;

let kpop l f = match l with 
	| Stack([]) -> f (Stack([]))
	| Stack(h :: t) -> f (kpop_helper (h) (Stack(t)));;

let halt l = match l with 
	| Stack(l) -> Stack(l);;

