// ...existing code...
        const payload = {
            ...form,
            creditLimit: form.creditLimit ? parseFloat(form.creditLimit) : null,
            salesRepEmployeeNumber: form.salesRepEmployeeNumber
                ? { employeeNumber: parseInt(form.salesRepEmployeeNumber) }
                : null
        };
// ...existing code...


