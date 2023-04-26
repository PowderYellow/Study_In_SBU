(* Nur Nibir
   111059470 *)
type bool_expr =
| Lit of string
| Not of bool_expr
| And of bool_expr * bool_expr
| Or of bool_expr * bool_expr
;;

let rec eval_express a b expression bool1 bool2 = match expression with
	|Lit (x) -> if x=a then bool1 else if x=b then bool2 else raise (Invalid_argument "Not valid ")
	|And (x,y) -> (eval_express a b x bool1 bool2) && (eval_express a b y bool1 bool2)
	|Or (x,y) -> (eval_express a b x bool1 bool2) || (eval_express a b y bool1 bool2)
	|Not (x)-> not(eval_express a b x bool1 bool2);;

let truth_table a b express = 
	try
    [(true , true , eval_express a b express true true);
	(true , false , eval_express a b express true false);
	(false , true , eval_express a b express false true);
	(false , false , eval_express a b express false false)]
	with Invalid_argument _-> [];;

