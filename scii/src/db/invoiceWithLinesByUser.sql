select il.invoice_oid, il.line_number, p.name, il.quantity 
from invoice_line il, invoice i, user_data ud, product p
where il.invoice_oid = i.oid
and il.product_id = p.server_id
and i.user_oid = 14
and i.creation_date = current_date
and ud.user_oid = 14
and p.product_list_oid = ud.product_list_oid
and ud.start_date = current_date
order by i.oid, il.line_number;

select * from invoice where user_oid = 10 order by invoice_list_oid, creation_date;

