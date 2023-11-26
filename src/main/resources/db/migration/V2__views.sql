CREATE VIEW invoice_view AS
SELECT i.*, c.fullname
from invoice i
join client c on c.id = i.client_id