%define next 0
%macro colon 2	
	%%next: dq next
    	db %1, 0
	%2: 
%define next %%next
%endmacro
