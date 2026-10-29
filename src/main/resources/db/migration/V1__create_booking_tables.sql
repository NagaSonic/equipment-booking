CREATE TABLE daily_equipment_inventory (
    id UUID PRIMARY KEY,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    updated_by VARCHAR(100) NOT NULL,
    booking_date DATE NOT NULL UNIQUE,
    total_quantity INTEGER NOT NULL DEFAULT 5,
    reserved_quantity INTEGER NOT NULL DEFAULT 0,
    CONSTRAINT chk_inventory_total CHECK (total_quantity = 5),
    CONSTRAINT chk_inventory_reserved CHECK (reserved_quantity >= 0 AND reserved_quantity <= total_quantity)
);
CREATE TABLE bookings (
    id UUID PRIMARY KEY,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    updated_by VARCHAR(100) NOT NULL,
    instructor_name VARCHAR(100) NOT NULL,
    booking_date DATE NOT NULL,
    quantity INTEGER NOT NULL,
    purpose VARCHAR(500) NOT NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT chk_booking_quantity CHECK (quantity BETWEEN 1 AND 5),
    CONSTRAINT chk_booking_status CHECK (status = 'PENDING')
);
CREATE INDEX idx_bookings_booking_date ON bookings(booking_date);
