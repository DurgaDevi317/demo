INSERT INTO seats(seat_no, booked_status) values('S1', 'Available') ON CONFLICT (seat_no) DO NOTHING;
INSERT INTO seats(seat_no, booked_status) values('S2', 'Booked') ON CONFLICT (seat_no) DO NOTHING;
INSERT INTO seats(seat_no, booked_status) values('S3', 'Available') ON CONFLICT (seat_no) DO NOTHING;
INSERT INTO seats(seat_no, booked_status) values('S4', 'Available') ON CONFLICT (seat_no) DO NOTHING;
INSERT INTO seats(seat_no, booked_status) values('S5', 'Booked') ON CONFLICT (seat_no) DO NOTHING;