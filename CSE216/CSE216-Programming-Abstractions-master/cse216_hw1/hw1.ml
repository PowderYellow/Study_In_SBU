(* Nur Nibir
   111059470 *)
let rec pow a b = 
	if b = 0 then 1
	else a * pow a (b-1);;

let rec float_pow x y = 
	if y = 0 then 1.0 
	else x *. float_pow x (y-1);;

let rec compress = function 
	| a::(b::_ as t) -> if a = b then compress t else a :: compress t
	| compressed -> compressed;;

let rec remove_if l f  = match l with 
    | [] -> []
    | h :: t -> if f h = true then remove_if t f else h :: remove_if t f;;

let rec slice list i k = match list with
    | [] -> failwith "Invalid values for indices"
    | h :: t -> let tail = if k=1 then [] else slice t (i-1) (k-1) in if i>0 then tail else h :: tail;;

let composition f g x = 
 	f g (x);;

let rec equiv_on f g = function
	| [] -> true
	| h :: t -> if f (h) != g (h) then false else equiv_on f g t;;

let rec pairwisefilter cmp lst = match lst with
  |[] -> []
  |[x] -> [x]
  |x::y::(_ as t)-> if cmp x y = x then x::pairwisefilter cmp t else y::pairwisefilter cmp t;;

let polynomial list input =
	let rec helpPoly input = function
	|[] -> 0
	|h::t -> (fst h) * pow input (snd h) + helpPoly input t in helpPoly input list;;

let rec powerset = function
      | [] -> [[]]
      | h :: t -> let pwrst = powerset t in pwrst @ List.map (fun rest -> h :: rest) pwrst;;

