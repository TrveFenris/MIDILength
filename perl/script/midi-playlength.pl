#!/usr/bin/perl
use strict;
use MIDI;

# by Jean-Pierre Vidal on the perl.midi maillist 
# jeanpierre.vidal@free.fr 

my $filename = shift;
die "unknown file $filename\n"   unless -e $filename;
die "usage : perl $0 midifile\n" unless defined $filename;

my $opus = new MIDI::Opus( { 'from_file' => $filename } );

#$opus->dump({'dump_tracks' => 1}); 

# ticks per quarter note 
my $tpqn = $opus->ticks();

my @tracks = $opus->tracks();

# convert tracks to score 
my @score = ();
for my $tr (@tracks) {
    my @events = $tr->events;
    my ( $score_r, $ticks ) = MIDI::Score::events_r_to_score_r( \@events );
    @score = ( @score, @$score_r );
}

my $score_r = MIDI::Score::sort_score_r( \@score );

#MIDI::Score::dump_score( $score_r ); 

my $duration    = 0;
my $tempo       = .5; #default for midi's with no set_tempo as per specs 
my $tempo_start = 0;
my $last_time   = 0;
for my $note (@$score_r) {
   #if($$note[0] eq 'set_tempo'){print $$note[0],"\n"}

   if ( $$note[0] eq 'set_tempo' ) {

        # if this 'tempo change' is not the last 'note', 
        # we will calculate another $last_time later 
        $last_time = 0;

        # 
        $duration += ( $$note[1] - $tempo_start ) / $tpqn * $tempo;

        # tempo in seconds per quarter note 
        $tempo       = $$note[2] / 1_000_000;
        $tempo_start = $$note[1];
    }
    elsif ( $$note[0] eq 'note' ) {

        # try to get the largest note after last tempo change 
        my $l_t = ( $$note[1] - $tempo_start ) + $$note[2];
        $last_time = $l_t if $l_t > $last_time;
    }
}

$duration += $last_time / $tpqn * $tempo;

my $dmn = int( $duration / 60 );
my $dms = $duration - $dmn * 60;
#printf "%d\n", $dmn;
#printf "%d\n", $dms;
printf "%.2f", $duration;
__END__ 

